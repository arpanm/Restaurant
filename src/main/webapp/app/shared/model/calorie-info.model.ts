import { IMealPlanSettings } from 'app/shared/model/meal-plan-settings.model';

export interface ICalorieInfo {
  id?: number;
  dailyCalorieLimit?: number | null;
  mealPlanSettings?: IMealPlanSettings | null;
}

export const defaultValue: Readonly<ICalorieInfo> = {};
