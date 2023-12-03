import { IDiscount } from 'app/shared/model/discount.model';
import { IMealPlanItem } from 'app/shared/model/meal-plan-item.model';
import { IMealPlanSettings } from 'app/shared/model/meal-plan-settings.model';
import { IApplicationUser } from 'app/shared/model/application-user.model';
import { ICartItem } from 'app/shared/model/cart-item.model';
import { IOrderItem } from 'app/shared/model/order-item.model';

export interface IMealPlan {
  id?: number;
  startDate?: string | null;
  endDate?: string | null;
  planName?: string | null;
  discount?: IDiscount | null;
  meals?: IMealPlanItem[] | null;
  mealPlanSetting?: IMealPlanSettings | null;
  usr?: IApplicationUser | null;
  cartItem?: ICartItem | null;
  orderItem?: IOrderItem | null;
}

export const defaultValue: Readonly<IMealPlan> = {};
