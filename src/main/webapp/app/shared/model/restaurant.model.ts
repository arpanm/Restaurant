import { ILocation } from 'app/shared/model/location.model';
import { IApplicationUser } from 'app/shared/model/application-user.model';
import { IItemPincodeMapping } from 'app/shared/model/item-pincode-mapping.model';
import { IOrderItem } from 'app/shared/model/order-item.model';
import { RestaurantType } from 'app/shared/model/enumerations/restaurant-type.model';

export interface IRestaurant {
  id?: number;
  restaurantName?: string | null;
  title?: string | null;
  logo?: string | null;
  type?: keyof typeof RestaurantType | null;
  location?: ILocation | null;
  admins?: IApplicationUser[] | null;
  itemPincodeMappings?: IItemPincodeMapping[] | null;
  orderItem?: IOrderItem | null;
}

export const defaultValue: Readonly<IRestaurant> = {};
