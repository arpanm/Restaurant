import { IItem } from 'app/shared/model/item.model';

export interface IFoodCategory {
  id?: number;
  catName?: string | null;
  description?: string | null;
  imageUrl?: string | null;
  items?: IItem[] | null;
}

export const defaultValue: Readonly<IFoodCategory> = {};
