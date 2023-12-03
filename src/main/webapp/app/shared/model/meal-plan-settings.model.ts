import { IWeightInfo } from 'app/shared/model/weight-info.model';
import { ICalorieInfo } from 'app/shared/model/calorie-info.model';
import { IMealPlan } from 'app/shared/model/meal-plan.model';
import { IAvoid } from 'app/shared/model/avoid.model';
import { DietType } from 'app/shared/model/enumerations/diet-type.model';
import { FoodType } from 'app/shared/model/enumerations/food-type.model';

export interface IMealPlanSettings {
  id?: number;
  dietType?: keyof typeof DietType | null;
  foodType?: keyof typeof FoodType | null;
  weightInfo?: IWeightInfo | null;
  calorieInfo?: ICalorieInfo | null;
  plans?: IMealPlan[] | null;
  avoidLists?: IAvoid[] | null;
}

export const defaultValue: Readonly<IMealPlanSettings> = {};
