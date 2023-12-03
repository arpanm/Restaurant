import { IItem } from 'app/shared/model/item.model';
import { IMealPlan } from 'app/shared/model/meal-plan.model';
import { ILocation } from 'app/shared/model/location.model';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { IApplicationUser } from 'app/shared/model/application-user.model';
import { IOrder } from 'app/shared/model/order.model';
import { OrderItemStatus } from 'app/shared/model/enumerations/order-item-status.model';

export interface IOrderItem {
  id?: number;
  quantity?: number | null;
  status?: keyof typeof OrderItemStatus | null;
  item?: IItem | null;
  meal?: IMealPlan | null;
  deliveryLoc?: ILocation | null;
  restaurant?: IRestaurant | null;
  orderAssignedTo?: IApplicationUser | null;
  order?: IOrder | null;
}

export const defaultValue: Readonly<IOrderItem> = {};
