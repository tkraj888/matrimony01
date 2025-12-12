package com.spring.jwt.ContactDetails;

import com.spring.jwt.CompleteProfile.CompleteProfileRepository;
import com.spring.jwt.CompleteProfile.CompleteProfileService;
import com.spring.jwt.HoroscopeDetails.ResourceAlreadyExistsException;
import com.spring.jwt.UserCredit.UserCreditRepository;
import com.spring.jwt.UserView.UserViewRepository;
import com.spring.jwt.entity.*;
import com.spring.jwt.exception.ResourceNotFoundException;
import com.spring.jwt.repository.UserRepository;
import com.spring.jwt.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactDetailsServiceImpl implements ContactDetailsService {

    private final ContactDetailsRepository contactRepo;
    private final UserRepository userRepo;
    private final CompleteProfileRepository completeProfileRepo;
    private final CompleteProfileService completeProfileService;
    private final UserCreditRepository userCreditRepo;
    private final UserViewRepository userViewRepo;
    private final SecurityUtil securityUtil;

    // CREATE
    @Override
    @Transactional
    public ContactDetailsDTO create(ContactDetailsDTO dto) {

        Integer userId = dto.getUserId();
        User user = userRepo.findById(Long.valueOf(userId))
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        if (contactRepo.findByUser_Id(userId).isPresent()) {
            throw new ResourceAlreadyExistsException("Contact details already exist for this user: " + userId);
        }
        ContactDetails entity = ContactDetailsMapper.toEntity(dto, user);
        ContactDetails saved = contactRepo.save(entity);
        CompleteProfile cp = completeProfileRepo.findByUser_Id(userId)
                .orElseGet(() -> {
                    CompleteProfile newCP = new CompleteProfile();
                    newCP.setUser(user);
                    newCP.setProfileCompleted(false);
                    return newCP;
                });

        cp.setContactDetails(saved);
        CompleteProfile persistedCP = completeProfileRepo.save(cp);
        completeProfileService.recalcAndSave(persistedCP);

        return ContactDetailsMapper.toDTO(saved);
    }

    // GET BY ID
    @Override
    public ContactDetailsDTO getById(Integer id) {
        ContactDetails entity = contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact details not found with ID: " + id));

        return ContactDetailsMapper.toDTO(entity);
    }

    // GET ALL
    @Override
    public List<ContactDetailsDTO> getAll() {
        return contactRepo.findAll()
                .stream()
                .map(ContactDetailsMapper::toDTO)
                .collect(Collectors.toList());
    }

    // UPDATE
    @Override
    @Transactional
    public ContactDetailsDTO update(Integer id, ContactDetailsDTO dto) {

        ContactDetails existing = contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact details not found with ID: " + id));

        // User cannot be changed
        User user = existing.getUser();

        // PATCH logic
        if (dto.getFullAddress() != null) existing.setFullAddress(dto.getFullAddress());
        if (dto.getCity() != null) existing.setCity(dto.getCity());
        if (dto.getPinCode() != null) existing.setPinCode(dto.getPinCode());
        if (dto.getMobileNumber() != null) existing.setMobileNumber(dto.getMobileNumber());
        if (dto.getAlternateNumber() != null) existing.setAlternateNumber(dto.getAlternateNumber());

        existing.setUser(user);

        ContactDetails saved = contactRepo.save(existing);

        return ContactDetailsMapper.toDTO(saved);
    }

    // DELETE
    @Override
    public void delete(Integer id) {
        ContactDetails entity = contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact details not found with ID: " + id));

        contactRepo.delete(entity);
    }

    @Override
    @Transactional
    public ContactDetailsDTO getByUserId(Integer targetUserId) {
        Integer viewerId = securityUtil.getLoggedInUserId();
        if (viewerId == null) {
            throw new UnauthorizedException("User not authenticated");
        }
        if (viewerId.equals(targetUserId)) {
            throw new IllegalStateException("You cannot view your own contact details");
        }
        User viewer = userRepo.findById(Long.valueOf(viewerId))
                .orElseThrow(() -> new ResourceNotFoundException("Viewer user not found"));

        User target = userRepo.findById(Long.valueOf(targetUserId))
                .orElseThrow(() -> new ResourceNotFoundException("Target user not found"));

        if (viewer.getGender() == target.getGender()) {
            throw new IllegalStateException("Access denied: Same-gender profiles cannot be viewed.");
        }
        ContactDetails details = contactRepo.findByUser_Id(targetUserId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contact details not found for userId: " + targetUserId));
        UserCredit credit = userCreditRepo.findByUserId(viewerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No subscription/credit found for userId: " + viewerId));
        LocalDate today = LocalDate.now();
        int todayViewCount = userViewRepo.countByUserIdAndDate(viewerId, today);
        if (todayViewCount >= credit.getDayCredit()) {
            throw new IllegalStateException("Daily view limit exceeded.");
        }
        if (credit.getBalanceCredit() <= 0) {
            throw new IllegalStateException("Insufficient credits.");
        }
        credit.setBalanceCredit(credit.getBalanceCredit() - 1);
        credit.setUseCredit(credit.getUseCredit() + 1);
        userCreditRepo.save(credit);
        UserView view = new UserView();
        view.setDate(today);
        view.setUserId(viewerId);
        view.setContactId(details.getContactId());
        view.setDayCredit(todayViewCount + 1);
        userViewRepo.save(view);

        return ContactDetailsMapper.toDTO(details);
    }




}
