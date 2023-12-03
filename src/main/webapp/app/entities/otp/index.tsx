import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Otp from './otp';
import OtpDetail from './otp-detail';
import OtpUpdate from './otp-update';
import OtpDeleteDialog from './otp-delete-dialog';

const OtpRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Otp />} />
    <Route path="new" element={<OtpUpdate />} />
    <Route path=":id">
      <Route index element={<OtpDetail />} />
      <Route path="edit" element={<OtpUpdate />} />
      <Route path="delete" element={<OtpDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OtpRoutes;
