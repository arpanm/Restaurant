import { IItem } from 'app/shared/model/item.model';
import { IRestaurant } from 'app/shared/model/restaurant.model';

export interface IItemPincodeMapping {
  id?: number;
  serviceability?: string | null;
  pincode?: string | null;
  item?: IItem | null;
  restaurant?: IRestaurant | null;
}

export const defaultValue: Readonly<IItemPincodeMapping> = {};
