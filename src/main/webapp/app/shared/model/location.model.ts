import { IApplicationUser } from 'app/shared/model/application-user.model';
import { IRestaurant } from 'app/shared/model/restaurant.model';

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
}

export const defaultValue: Readonly<ILocation> = {};
