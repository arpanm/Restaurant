import { IQuantity } from 'app/shared/model/quantity.model';

export interface IQtyUnit {
  id?: number;
  unitName?: string | null;
  quantity?: IQuantity | null;
}

export const defaultValue: Readonly<IQtyUnit> = {};
