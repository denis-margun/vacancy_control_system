package by.klishevich.vacancy_control_system.controllers;

import by.klishevich.vacancy_control_system.criteria.ScheduleCriteria;
import by.klishevich.vacancy_control_system.dto.shedule.ScheduleCreateEditRequest;
import by.klishevich.vacancy_control_system.dto.shedule.ScheduleDto;
import by.klishevich.vacancy_control_system.entity.PageDto;
import by.klishevich.vacancy_control_system.service.ScheduleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = "/api/v1/schedules")
public class ScheduleController {
    private final ScheduleService service;

    @GetMapping(params = {"sortBy", "sortDirection", "pageNumber", "pageSize"})
    public ResponseEntity<PageDto<ScheduleDto>> findAll(
            ScheduleCriteria criteria
    ) {
        return new ResponseEntity<>(service.findAll(criteria), HttpStatus.OK);
    }

    @GetMapping(params = {"ids"})
    public ResponseEntity<List<ScheduleDto>> findAllByIds(
            @RequestParam(value = "ids") List<Long> ids
    ) {
        return new ResponseEntity<>(service.findAllByIds(ids), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ScheduleDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_DIRECTOR')")
    @PostMapping()
    public ResponseEntity<ScheduleDto> create(
            @Valid @RequestBody ScheduleCreateEditRequest request
    ) {
        return new ResponseEntity<>(service.create(request), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_DIRECTOR')")
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDto> update(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleCreateEditRequest request
    ) {
        return new ResponseEntity<>(service.update(id, request), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_DIRECTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_DIRECTOR')")
    @DeleteMapping()
    public ResponseEntity<Void> delete(
            @RequestParam(value = "ids") List<Long> ids
    ) {
        service.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
