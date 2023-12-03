import { IMealPlan } from 'app/shared/model/meal-plan.model';
import { DiscountSlab } from 'app/shared/model/enumerations/discount-slab.model';

export interface IDiscount {
  id?: number;
  discount?: number | null;
  slab?: keyof typeof DiscountSlab | null;
  mealPlan?: IMealPlan | null;
}

export const defaultValue: Readonly<IDiscount> = {};
