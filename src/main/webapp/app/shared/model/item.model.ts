import { INutrition } from 'app/shared/model/nutrition.model';
import { IPrice } from 'app/shared/model/price.model';
import { IQuantity } from 'app/shared/model/quantity.model';
import { IIngredienceMaster } from 'app/shared/model/ingredience-master.model';
import { IFoodCategory } from 'app/shared/model/food-category.model';
import { FoodType } from 'app/shared/model/enumerations/food-type.model';

export interface IItem {
  id?: number;
  itemName?: string | null;
  imageUrl?: string | null;
  properties?: number | null;
  foodType?: keyof typeof FoodType | null;
  nutrition?: INutrition | null;
  price?: IPrice | null;
  quantity?: IQuantity | null;
  ingrediences?: IIngredienceMaster[] | null;
  category?: IFoodCategory | null;
}

export const defaultValue: Readonly<IItem> = {};
