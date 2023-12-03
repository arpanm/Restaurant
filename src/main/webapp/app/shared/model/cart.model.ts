import { ICartItem } from 'app/shared/model/cart-item.model';
import { IApplicationUser } from 'app/shared/model/application-user.model';

export interface ICart {
  id?: number;
  status?: string | null;
  items?: ICartItem[] | null;
  usr?: IApplicationUser | null;
}

export const defaultValue: Readonly<ICart> = {};
