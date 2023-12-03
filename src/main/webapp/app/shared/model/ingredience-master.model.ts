import { IItem } from 'app/shared/model/item.model';

export interface IIngredienceMaster {
  id?: number;
  ingredience?: string | null;
  items?: IItem[] | null;
}

export const defaultValue: Readonly<IIngredienceMaster> = {};
