package ir.behpardakht.zoneAndAreaManagement.controller;

import ir.behpardakht.zoneAndAreaManagement.model.dto.AreaDto;
import ir.behpardakht.zoneAndAreaManagement.model.dto.ZoneDto;
import ir.behpardakht.zoneAndAreaManagement.service.AreaService;
import ir.behpardakht.zoneAndAreaManagement.service.ZoneService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/page/areas")
public class AreaViewController {

    private final AreaService areaService;
    private final ZoneService zoneService;

    @GetMapping("/get-all-areas")
    public String getAllAreas(Model model) {
        List<AreaDto> areas = areaService.getAllAreas();
        model.addAttribute("areas", areas);
        return "area-list";
    }

    @GetMapping("/create-area")
    public String createAreaForm(Model model) {
        model.addAttribute("areaDto", new AreaDto());
        return "create-area";
    }

    @PostMapping("/create")
    public ResponseEntity<String> createArea(@ModelAttribute AreaDto areaDto) {
        try {
            areaService.saveArea(areaDto);
            return ResponseEntity.ok("منطقه با موفقیت اضافه شد!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("خطا در اضافه کردن منطقه!");
        }
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @ModelAttribute
    public void preventCache(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.
    }

    @GetMapping("/edit-area")
    public String editAreaForm(@RequestParam(name = "code") String code, Model model) {
        AreaDto areaDto = areaService.getAreaByCode(code);
        model.addAttribute("areaDto", areaDto);
        return "edit-area";
    }

    @PostMapping("/edit")
    public String updateArea(@ModelAttribute AreaDto areaDto, Model model) {
        try {
            areaService.updateArea(areaDto.getCode(), areaDto);
            model.addAttribute("message", "منطقه با موفقیت ویرایش شد!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "خطا در ویرایش منطقه!");
        }
        model.addAttribute("areaDto", areaDto);
        return "edit-area";
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteArea(@RequestParam(name = "code") String code) {
        try {
            areaService.deleteArea(code);
            return ResponseEntity.ok("منطقه با موفقیت حذف شد!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("خطا در حذف منطقه!");
        }
    }
    @GetMapping("/create-zones")
    public String showCreateZoneForm(@RequestParam("areaCode") String areaCode, Model model) {
        ZoneDto zoneDto = new ZoneDto();
        zoneDto.setAreaCode(areaCode); // Associate with the specific area code
        model.addAttribute("zone", zoneDto);
        return "zone-form"; // Thymeleaf template for creating a zone
    }

    @PostMapping("/create-zone")
    public String createZone(@ModelAttribute ZoneDto zone, RedirectAttributes redirectAttributes) {
        try {
            zoneService.saveZone(zone);
            redirectAttributes.addFlashAttribute("message", "ناحیه با موفقیت ایجاد شد.");
            redirectAttributes.addFlashAttribute("success", true);
            return "redirect:/page/areas";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", "خطا در ایجاد ناحیه.");
            redirectAttributes.addFlashAttribute("success", false);
            return "redirect:/page/areas";
        }
    }
}