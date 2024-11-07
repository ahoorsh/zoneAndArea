package ir.behpardakht.zoneAndAreaManagement.service;

import ir.behpardakht.zoneAndAreaManagement.model.AreaModel;
import ir.behpardakht.zoneAndAreaManagement.model.ZoneModel;
import ir.behpardakht.zoneAndAreaManagement.model.dto.ZoneDto;
import ir.behpardakht.zoneAndAreaManagement.model.dto.mapper.ZoneMapper;
import ir.behpardakht.zoneAndAreaManagement.repository.AreaRepository;
import ir.behpardakht.zoneAndAreaManagement.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoneService {

    private final ZoneRepository zoneRepository;
    private final AreaRepository areaRepository;
    private final ZoneMapper zoneMapper;

    public List<ZoneDto> getAllZones() {
        return zoneMapper.toDTOs(zoneRepository.findAll());
    }

    public List<ZoneDto> getZonesByAreaCode(String areaCode) {
        AreaModel areaModel = areaRepository.findByCode(areaCode);
        if (areaModel == null) {
            throw new RuntimeException("Area not found for code: " + areaCode);
        }

        List<ZoneModel> zoneModels = zoneRepository.findByArea(areaModel);
        return zoneMapper.toDTOs(zoneModels);
    }


    public ZoneDto saveZone(ZoneDto zone) {
        AreaModel areaModel = areaRepository.findAreaModelByCode(zone.getAreaCode());
        if (areaModel == null) {
            throw new RuntimeException("Area does not exist");
        }

        ZoneModel zoneModel = zoneMapper.toEntity(zone);
        zoneModel.setArea(areaModel);
        zoneRepository.saveAndFlush(zoneModel);
        return zoneMapper.toDTO(zoneModel);
    }

    public ZoneDto updateZone(Long id, ZoneDto zone) {
        ZoneModel existingZone = zoneRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        AreaModel areaModel = areaRepository.findAreaModelByCode(zone.getAreaCode());
        existingZone.setName(zone.getName());
        existingZone.setCode(zone.getCode());
        existingZone.setArea(areaModel);
        zoneRepository.save(existingZone);
        return zoneMapper.toDTO(existingZone);
    }

    public ZoneDto deleteZone(Long id) {
        ZoneModel existingZone = zoneRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        zoneRepository.deleteById(id);
        return zoneMapper.toDTO(existingZone);
    }
}
