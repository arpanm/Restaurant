import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SkipDate from './skip-date';
import SkipDateDetail from './skip-date-detail';
import SkipDateUpdate from './skip-date-update';
import SkipDateDeleteDialog from './skip-date-delete-dialog';

const SkipDateRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SkipDate />} />
    <Route path="new" element={<SkipDateUpdate />} />
    <Route path=":id">
      <Route index element={<SkipDateDetail />} />
      <Route path="edit" element={<SkipDateUpdate />} />
      <Route path="delete" element={<SkipDateDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SkipDateRoutes;
