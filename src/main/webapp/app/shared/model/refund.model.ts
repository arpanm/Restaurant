import { IPayment } from 'app/shared/model/payment.model';
import { RefundStatus } from 'app/shared/model/enumerations/refund-status.model';
import { PaymentVendor } from 'app/shared/model/enumerations/payment-vendor.model';

export interface IRefund {
  id?: number;
  status?: keyof typeof RefundStatus | null;
  vendor?: keyof typeof PaymentVendor | null;
  amount?: number | null;
  initDate?: string | null;
  updatedDate?: string | null;
  payment?: IPayment | null;
}

export const defaultValue: Readonly<IRefund> = {};
