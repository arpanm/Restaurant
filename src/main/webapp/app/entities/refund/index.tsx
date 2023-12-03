import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Refund from './refund';
import RefundDetail from './refund-detail';
import RefundUpdate from './refund-update';
import RefundDeleteDialog from './refund-delete-dialog';

const RefundRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Refund />} />
    <Route path="new" element={<RefundUpdate />} />
    <Route path=":id">
      <Route index element={<RefundDetail />} />
      <Route path="edit" element={<RefundUpdate />} />
      <Route path="delete" element={<RefundDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RefundRoutes;
