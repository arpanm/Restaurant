import { ICart } from 'app/shared/model/cart.model';

export interface ICoupon {
  id?: number;
  maxValue?: number | null;
  maxPercentage?: number | null;
  minOrderValue?: number | null;
  cart?: ICart | null;
}

export const defaultValue: Readonly<ICoupon> = {};
