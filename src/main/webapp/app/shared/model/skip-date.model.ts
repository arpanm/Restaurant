import { IMealPlanItem } from 'app/shared/model/meal-plan-item.model';

export interface ISkipDate {
  id?: number;
  skipDate?: string | null;
  mealPlanItem?: IMealPlanItem | null;
}

export const defaultValue: Readonly<ISkipDate> = {};
