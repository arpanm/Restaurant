import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Quantity from './quantity';
import QuantityDetail from './quantity-detail';
import QuantityUpdate from './quantity-update';
import QuantityDeleteDialog from './quantity-delete-dialog';

const QuantityRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Quantity />} />
    <Route path="new" element={<QuantityUpdate />} />
    <Route path=":id">
      <Route index element={<QuantityDetail />} />
      <Route path="edit" element={<QuantityUpdate />} />
      <Route path="delete" element={<QuantityDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default QuantityRoutes;
