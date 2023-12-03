import { ILocation } from 'app/shared/model/location.model';
import { IOrderItem } from 'app/shared/model/order-item.model';
import { IPayment } from 'app/shared/model/payment.model';
import { ICoupon } from 'app/shared/model/coupon.model';
import { IApplicationUser } from 'app/shared/model/application-user.model';
import { OrderStatus } from 'app/shared/model/enumerations/order-status.model';

export interface IOrder {
  id?: number;
  status?: keyof typeof OrderStatus | null;
  orderDate?: string | null;
  amount?: number | null;
  couponValue?: number | null;
  billingLoc?: ILocation | null;
  items?: IOrderItem[] | null;
  payments?: IPayment[] | null;
  coupon?: ICoupon | null;
  usr?: IApplicationUser | null;
  statusUpdatedBy?: IApplicationUser | null;
}

export const defaultValue: Readonly<IOrder> = {};
