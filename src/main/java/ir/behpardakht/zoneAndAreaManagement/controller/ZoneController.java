package ir.behpardakht.zoneAndAreaManagement.controller;

import ir.behpardakht.zoneAndAreaManagement.model.dto.ZoneDto;
import ir.behpardakht.zoneAndAreaManagement.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;

    @GetMapping("/byAreaCode")
    public ResponseEntity<List<ZoneDto>> getZonesByAreaCode(@RequestParam String areaCode) {
        try {
            List<ZoneDto> zoneDtos = zoneService.getZonesByAreaCode(areaCode);
            return ResponseEntity.ok(zoneDtos);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
    }


    @GetMapping("/get-all-zones")
    public List<ZoneDto> getAllZones() {
        return zoneService.getAllZones();
    }

    @PostMapping("/create")
    public ResponseEntity<ZoneDto> createZone(@RequestBody ZoneDto zone) {
        try {
            ZoneDto zoneModel = zoneService.saveZone(zone);
            return new ResponseEntity<>(zoneModel, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @PatchMapping("/edit")
    public ResponseEntity<ZoneDto> updateZone(@RequestParam(name = "zoneId") long id, @RequestBody ZoneDto zoneDto) {
        ZoneDto updatedZone = zoneService.updateZone(id, zoneDto);
        return new ResponseEntity<>(updatedZone, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ZoneDto> deleteZone(@RequestParam(name = "zoneId") long id) {
        ZoneDto deletedZone = zoneService.deleteZone(id);
        return new ResponseEntity<>(deletedZone, HttpStatus.OK);
    }
}
