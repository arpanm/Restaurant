import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Avoid from './avoid';
import AvoidDetail from './avoid-detail';
import AvoidUpdate from './avoid-update';
import AvoidDeleteDialog from './avoid-delete-dialog';

const AvoidRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Avoid />} />
    <Route path="new" element={<AvoidUpdate />} />
    <Route path=":id">
      <Route index element={<AvoidDetail />} />
      <Route path="edit" element={<AvoidUpdate />} />
      <Route path="delete" element={<AvoidDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AvoidRoutes;
