package ir.behpardakht.zoneAndAreaManagement.model.dto.mapper;

import ir.behpardakht.zoneAndAreaManagement.model.ZoneModel;
import ir.behpardakht.zoneAndAreaManagement.model.dto.ZoneDto;

import java.util.List;

public interface ZoneMapper {

    ZoneDto toDTO(ZoneModel e);

    ZoneModel toEntity(ZoneDto d);

    List<ZoneDto> toDTOs(List<ZoneModel> e);

    List<ZoneModel> toEntities(List<ZoneDto> e);
}