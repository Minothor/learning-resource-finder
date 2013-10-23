package learningresourcefinder.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import learningresourcefinder.model.Rating;
import learningresourcefinder.model.Resource;
import learningresourcefinder.repository.RatingRepository;
import learningresourcefinder.security.SecurityContext;
import learningresourcefinder.util.EnumUtil;

import org.springframework.web.servlet.ModelAndView;

public class ModelAndViewUtil {

    public static void addRatingMapToModelAndView(ModelAndView mv, List<Resource> resourceList) {
        List<Rating> listRating = ((RatingRepository) ContextUtil.getSpringBean(RatingRepository.class)).listRating(resourceList, SecurityContext.getUser());
        Map<Resource, Rating> mapRating = new HashMap<Resource, Rating>();
        for(Rating rating : listRating) {
            mapRating.put(rating.getResource(), rating);
        }
        mv.addObject("mapRating", mapRating);
    }

    public static void addRatingMapToModelAndView(ModelAndView mv, Resource resource) {
        List<Resource> oneResourceList = new ArrayList<>();
        oneResourceList.add(resource);
        addRatingMapToModelAndView(mv, oneResourceList);
    }

    public static void addDataEnumToModelAndView(ModelAndView mv, Class<?> enumClass) {
        mv.addObject("dataEnum"+enumClass.getSimpleName(), enumValuesToJSON(enumClass));
    }

    /** Build a string with all the valued of an enum, like that:
     *  "[{value:1, text:'FIRST_ENUM_VALUE'}, {value:2, text:'SECOND_ENUMVALUE'}]" */
    public static String enumValuesToJSON(Class enumClass) {
        int i = 1, sizeplatform = EnumUtil.getValues(enumClass).length;
        String dataEnum = "[";

        for (Object enumValue : EnumUtil.getValues(enumClass)) {
            dataEnum += "{value:" + i + "," + "text:" + "'" + EnumUtil.getDescription(enumValue) + "'}";
            if (i == sizeplatform) {
                dataEnum += "]";
                break;
            }
            dataEnum += ",";
            i++;
        }
        return dataEnum;
    }

}
