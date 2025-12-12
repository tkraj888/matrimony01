package com.spring.jwt.ContactDetails;

import com.spring.jwt.dto.ResponseDto;
import com.spring.jwt.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactDetailsController {

    private final ContactDetailsService service;

    @PostMapping
    public ResponseEntity<ResponseDto<?>> create(@RequestBody ContactDetailsDTO dto) {
        try {
            return ResponseEntity.ok(ResponseDto.success("Created successfully", service.create(dto)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.error("Failed to create", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> get(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(ResponseDto.success("Record found", service.getById(id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.error("Failed to fetch", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseDto<?>> getAll() {
        return ResponseEntity.ok(ResponseDto.success("List fetched", service.getAll()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> update(@PathVariable Integer id, @RequestBody ContactDetailsDTO dto) {
        try {
            return ResponseEntity.ok(ResponseDto.success("Updated successfully", service.update(id, dto)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.error("Update failed", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> delete(@PathVariable Integer id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(ResponseDto.success("Deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.error("Delete failed", e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseDto<ContactDetailsDTO>> getByUserId(@PathVariable Integer userId) {
        try {
            ContactDetailsDTO dto = service.getByUserId(userId);
            return ResponseEntity.ok(ResponseDto.success("Contact details fetched", dto));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseDto.error("Not Found", ex.getMessage()));
        }
    }
}
