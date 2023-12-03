import { IMealPlanSettings } from 'app/shared/model/meal-plan-settings.model';

export interface IAvoid {
  id?: number;
  avoidIngredience?: string | null;
  mealPlanSettings?: IMealPlanSettings[] | null;
}

export const defaultValue: Readonly<IAvoid> = {};
