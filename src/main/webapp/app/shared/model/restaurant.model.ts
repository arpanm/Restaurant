import { ILocation } from 'app/shared/model/location.model';
import { IApplicationUser } from 'app/shared/model/application-user.model';
import { RestaurantType } from 'app/shared/model/enumerations/restaurant-type.model';

export interface IRestaurant {
  id?: number;
  restaurantName?: string | null;
  title?: string | null;
  logo?: string | null;
  type?: keyof typeof RestaurantType | null;
  location?: ILocation | null;
  admins?: IApplicationUser[] | null;
}

export const defaultValue: Readonly<IRestaurant> = {};
