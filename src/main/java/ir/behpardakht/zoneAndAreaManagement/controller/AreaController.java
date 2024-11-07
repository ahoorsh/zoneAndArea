package ir.behpardakht.zoneAndAreaManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.behpardakht.zoneAndAreaManagement.model.dto.AreaDto;
import ir.behpardakht.zoneAndAreaManagement.service.AreaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/areas")
@RequiredArgsConstructor
@Tag(name = "Area Management", description = "Operations related to areas")
public class AreaController {

    private final AreaService areaService;

    @Operation(summary = "Get area")
    @GetMapping("/get-area")
    public ResponseEntity<AreaDto> getArea(@RequestParam(name = "areaId") long id) {
        AreaDto area = areaService.getAreaById(id);
        return new ResponseEntity<>(area, HttpStatus.OK);
    }

    @Operation(summary = "Get all areas")
    @GetMapping("/get-all-areas")
    public List<AreaDto> getAreas() {
        return areaService.getAllAreas();
    }

    @Operation(summary = "Create a new area")
    @PostMapping("/create")
    public ResponseEntity<AreaDto> createArea(@RequestBody @Valid AreaDto area) {
        AreaDto areaDto = areaService.saveArea(area);
        return new ResponseEntity<>(areaDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an area")
    @PatchMapping("/edit")
    public ResponseEntity<AreaDto> updateArea(@RequestParam(name = "code") String code, @RequestBody AreaDto areaDto) {
        AreaDto updatedArea = areaService.updateArea(code, areaDto);
        return new ResponseEntity<>(updatedArea, HttpStatus.OK);
    }

    @Operation(summary = "Delete an area")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteArea(@RequestParam(name = "code") String code) {
        areaService.deleteArea(code);
        return new ResponseEntity<>("منطقه با موفقیت حذف شد!", HttpStatus.OK);
    }
}