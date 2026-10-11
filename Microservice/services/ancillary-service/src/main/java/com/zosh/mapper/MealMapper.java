package com.zosh.mapper;

import com.zosh.model.Meal;
import com.zosh.payload.request.MealRequest;
import com.zosh.payload.response.MealResponse;

public class MealMapper {
    /*public static Meal toEntity(MealRequest request){
        Meal.builder()
                .code(request.getCode())
                .name(request.getName())
                .mealType(request.getMealType())
                .dietaryRestriction(request.getDietaryRestriction())
                .ingredients(request.getIngredients())
                .imageUrl(request.getImageUrl())
                .requiredAdvancedBookings(request.getRequiredAdvanceBooking()!=null ?
                        request.getRequiredAdvanceBooking():false)
                .advancedBookingsHours(request.getAdvanceBookingHours())
                .displayOrder(request.getDisplayOrder()!=null ? request.getDisplayOrder():0)
                .airlineId(airlineId)
                .build();
    }*/

    public static MealResponse toResponse(Meal meal){
        if(meal==null) return null;

        return MealResponse.builder()
                .id(meal.getId())
                .code(meal.getCode())
                .name(meal.getName())
                .mealType(meal.getMealType())
                .dietaryRestriction(meal.getDietaryRestriction())
                .ingredients(meal.getIngredients())
                .imageUrl(meal.getImageUrl())
                .available(meal.getAvailable())
                .requiredAdvanceBooking(meal.getRequiredAdvancedBookings())
                .displayOrder( meal.getDisplayOrder() !=null ? meal.getDisplayOrder():0)
                .airlineId(meal.getAirlineId())
                .createdAt( meal.getCreatedAt() !=null ? meal.getCreatedAt():null)
                .updatedAt( meal.getUpdatedAt() !=null ? meal.getUpdatedAt():null)
                .build();
    }
}
