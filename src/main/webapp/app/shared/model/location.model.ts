import { IApplicationUser } from 'app/shared/model/application-user.model';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { IMealPlanItem } from 'app/shared/model/meal-plan-item.model';
import { IOrderItem } from 'app/shared/model/order-item.model';
import { IOrder } from 'app/shared/model/order.model';

export interface ILocation {
  id?: number;
  saveAs?: string | null;
  gst?: string | null;
  pan?: string | null;
  email?: string | null;
  phone?: string | null;
  streetAddress?: string | null;
  postalCode?: string | null;
  area?: string | null;
  city?: string | null;
  stateProvince?: string | null;
  country?: string | null;
  latitude?: number | null;
  longitude?: number | null;
  usr?: IApplicationUser | null;
  restaurant?: IRestaurant | null;
  mealPlanItem?: IMealPlanItem | null;
  orderItem?: IOrderItem | null;
  order?: IOrder | null;
}

export const defaultValue: Readonly<ILocation> = {};
