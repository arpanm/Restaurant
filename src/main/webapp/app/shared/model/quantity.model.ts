import { IQtyUnit } from 'app/shared/model/qty-unit.model';
import { IItem } from 'app/shared/model/item.model';

export interface IQuantity {
  id?: number;
  quantity?: number | null;
  minQuantity?: number | null;
  qtyUnit?: IQtyUnit | null;
  item?: IItem | null;
}

export const defaultValue: Readonly<IQuantity> = {};
