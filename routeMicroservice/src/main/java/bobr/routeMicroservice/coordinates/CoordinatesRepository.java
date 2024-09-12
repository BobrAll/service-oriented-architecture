package bobr.routeMicroservice.coordinates;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinatesRepository extends PagingAndSortingRepository<Coordinates, Integer> {
}
