package bobr.routeMicroservice.mappers;

import bobr.routeMicroservice.location.Location;
import https.help_me.please.CreateLocationRequest;
import https.help_me.please.SoapLocation;
import https.help_me.please.UpdateLocationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface LocationMapper {

    public abstract Location map(SoapLocation location);

    public abstract SoapLocation map(Location location);

    public abstract Location map(CreateLocationRequest location);

    public abstract Location map(UpdateLocationRequest location);

}