import { IMealPlanSettings } from 'app/shared/model/meal-plan-settings.model';

export interface IWeightInfo {
  id?: number;
  currentWeight?: number | null;
  expectedWeight?: number | null;
  heightInInch?: number | null;
  numberOfDays?: number | null;
  mealPlanSettings?: IMealPlanSettings | null;
}

export const defaultValue: Readonly<IWeightInfo> = {};
