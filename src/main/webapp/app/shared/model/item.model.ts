import { INutrition } from 'app/shared/model/nutrition.model';
import { IPrice } from 'app/shared/model/price.model';
import { IQuantity } from 'app/shared/model/quantity.model';
import { IItemPincodeMapping } from 'app/shared/model/item-pincode-mapping.model';
import { IIngredienceMaster } from 'app/shared/model/ingredience-master.model';
import { IFoodCategory } from 'app/shared/model/food-category.model';
import { IMealPlanItem } from 'app/shared/model/meal-plan-item.model';
import { ICartItem } from 'app/shared/model/cart-item.model';
import { IOrderItem } from 'app/shared/model/order-item.model';
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
  itemPincodeMappings?: IItemPincodeMapping[] | null;
  ingrediences?: IIngredienceMaster[] | null;
  category?: IFoodCategory | null;
  mealPlanItems?: IMealPlanItem[] | null;
  cartItem?: ICartItem | null;
  orderItem?: IOrderItem | null;
}

export const defaultValue: Readonly<IItem> = {};
