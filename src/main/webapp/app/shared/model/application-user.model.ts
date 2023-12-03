import { ILocation } from 'app/shared/model/location.model';
import { IOtp } from 'app/shared/model/otp.model';
import { IMealPlan } from 'app/shared/model/meal-plan.model';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { UserRole } from 'app/shared/model/enumerations/user-role.model';

export interface IApplicationUser {
  id?: number;
  name?: string | null;
  password?: string | null;
  email?: string | null;
  phone?: string | null;
  role?: keyof typeof UserRole | null;
  isPhoneValidated?: boolean | null;
  isEmailValidated?: boolean | null;
  phoneValidatedOn?: string | null;
  emailValidatedOn?: string | null;
  addresses?: ILocation[] | null;
  otps?: IOtp[] | null;
  mealPlans?: IMealPlan[] | null;
  restaurant?: IRestaurant | null;
}

export const defaultValue: Readonly<IApplicationUser> = {
  isPhoneValidated: false,
  isEmailValidated: false,
};
