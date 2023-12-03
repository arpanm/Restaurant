import { IItem } from 'app/shared/model/item.model';
import { NutritionType } from 'app/shared/model/enumerations/nutrition-type.model';

export interface INutrition {
  id?: number;
  nutritionValue?: number | null;
  nutriType?: keyof typeof NutritionType | null;
  item?: IItem | null;
}

export const defaultValue: Readonly<INutrition> = {};
