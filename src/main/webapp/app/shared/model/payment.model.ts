import { IRefund } from 'app/shared/model/refund.model';
import { IOrder } from 'app/shared/model/order.model';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';
import { PaymentVendor } from 'app/shared/model/enumerations/payment-vendor.model';
import { PaymentType } from 'app/shared/model/enumerations/payment-type.model';

export interface IPayment {
  id?: number;
  status?: keyof typeof PaymentStatus | null;
  vendor?: keyof typeof PaymentVendor | null;
  type?: keyof typeof PaymentType | null;
  amount?: number | null;
  initDate?: string | null;
  updatedDate?: string | null;
  totalRefundAmount?: number | null;
  refunds?: IRefund[] | null;
  order?: IOrder | null;
}

export const defaultValue: Readonly<IPayment> = {};
