package uz.mu.lms.service.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;
import uz.mu.lms.dto.AttachmentDto;
import uz.mu.lms.model.Attachment;

@Mapper(componentModel = "spring")
public abstract class AttachmentMapper implements AbstractMapper<Attachment, AttachmentDto> {

    @Value("${spring.defaultValues.host}")
    String hostAddr;

    @Mapping(target = "contentUrl", source = "id", qualifiedByName = "generateUrl")
    public abstract AttachmentDto toDto(Attachment entity);

    @Named("generateUrl")
    public String generateUrls(Integer id) {
        return hostAddr + "/api/attachment/" + id;
    }
}


