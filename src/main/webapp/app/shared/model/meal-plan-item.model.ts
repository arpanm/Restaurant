import { ILocation } from 'app/shared/model/location.model';
import { ISkipDate } from 'app/shared/model/skip-date.model';
import { IItem } from 'app/shared/model/item.model';
import { IMealPlan } from 'app/shared/model/meal-plan.model';

export interface IMealPlanItem {
  id?: number;
  planItemName?: string | null;
  hourValue?: number | null;
  pincode?: string | null;
  deliveryLocation?: ILocation | null;
  skipDates?: ISkipDate[] | null;
  items?: IItem[] | null;
  plan?: IMealPlan | null;
}

export const defaultValue: Readonly<IMealPlanItem> = {};
