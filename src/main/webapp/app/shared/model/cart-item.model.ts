import { IItem } from 'app/shared/model/item.model';
import { IMealPlan } from 'app/shared/model/meal-plan.model';
import { ICart } from 'app/shared/model/cart.model';

export interface ICartItem {
  id?: number;
  quantity?: number | null;
  item?: IItem | null;
  meal?: IMealPlan | null;
  cart?: ICart | null;
}

export const defaultValue: Readonly<ICartItem> = {};
