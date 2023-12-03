import { IMealPlanItem } from 'app/shared/model/meal-plan-item.model';
import { IMealPlanSettings } from 'app/shared/model/meal-plan-settings.model';
import { IApplicationUser } from 'app/shared/model/application-user.model';

export interface IMealPlan {
  id?: number;
  startDate?: string | null;
  endDate?: string | null;
  planName?: string | null;
  meals?: IMealPlanItem[] | null;
  mealPlanSetting?: IMealPlanSettings | null;
  usr?: IApplicationUser | null;
}

export const defaultValue: Readonly<IMealPlan> = {};
