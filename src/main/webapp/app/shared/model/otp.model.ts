import { IApplicationUser } from 'app/shared/model/application-user.model';

export interface IOtp {
  id?: number;
  email?: string | null;
  phone?: string | null;
  otp?: number | null;
  isActive?: boolean | null;
  isValidated?: boolean | null;
  expiry?: string | null;
  usr?: IApplicationUser | null;
}

export const defaultValue: Readonly<IOtp> = {
  isActive: false,
  isValidated: false,
};
