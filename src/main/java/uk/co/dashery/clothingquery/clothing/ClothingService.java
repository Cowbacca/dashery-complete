package uk.co.dashery.clothingquery.clothing;

import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class ClothingService {

    public static final String SEPARATOR = ",";
    @Inject
    private ClothingRepository clothingRepository;

    public List<Clothing> search(String search) {
        String[] tags = search.split(SEPARATOR);
        List<Clothing> clothingList = clothingRepository.findByAllTagsIn(tags);
        clothingList.sort((o1, o2) -> -o1.compareRelevance(o2, tags));
        return clothingList;
    }
}
