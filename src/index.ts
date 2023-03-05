import { registerPlugin } from '@capacitor/core';

import type { CapacitorForegroundServicePlugin } from './definitions';

const CapacitorForegroundService =
  registerPlugin<CapacitorForegroundServicePlugin>(
    'CapacitorForegroundService',
    {
      web: () =>
        import('./web').then(m => new m.CapacitorForegroundServiceWeb()),
    },
  );

export * from './definitions';
export { CapacitorForegroundService };
