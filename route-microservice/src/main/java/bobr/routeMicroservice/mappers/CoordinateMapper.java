package bobr.routeMicroservice.mappers;

import bobr.routeMicroservice.coordinates.Coordinates;
import https.help_me.please.CreateCoordinatesRequest;
import https.help_me.please.SoapCoordinates;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CoordinateMapper {

    public abstract Coordinates map(SoapCoordinates coordinates);

    public abstract Coordinates map(CreateCoordinatesRequest coordinates);

    public abstract SoapCoordinates map(Coordinates coordinates);

}
