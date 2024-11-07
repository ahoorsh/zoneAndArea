package ir.behpardakht.zoneAndAreaManagement.controller;

import ir.behpardakht.zoneAndAreaManagement.model.dto.ZoneDto;
import ir.behpardakht.zoneAndAreaManagement.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/page/zones")
public class ZoneViewController {

    private final ZoneService zoneService;

    @GetMapping("/create-zone")
    public String showCreateZoneForm(@RequestParam("areaCode") String areaCode, Model model) {
        ZoneDto zoneDto = new ZoneDto();
        zoneDto.setAreaCode(areaCode); // Prepopulate the areaCode in the form
        model.addAttribute("zoneDto", zoneDto);
        model.addAttribute("areaCode", areaCode); // Pass areaCode for the hidden field
        return "zone-form"; // Thymeleaf template for creating a zone
    }

    @GetMapping("/get-all-zones")
    public String getAllZones(Model model) {
        // Add logic to get all zones from the ZoneService, similar to getAllAreas in AreaViewController
        model.addAttribute("zones", zoneService.getAllZones());
        return "zone-list"; // Thymeleaf template to list zones
    }
}
