import { IItem } from 'app/shared/model/item.model';

export interface IPrice {
  id?: number;
  mrp?: number | null;
  price?: number | null;
  discountPercentage?: number | null;
  item?: IItem | null;
}

export const defaultValue: Readonly<IPrice> = {};
