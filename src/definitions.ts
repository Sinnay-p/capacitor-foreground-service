export interface CapacitorForegroundServicePlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  startService(options: StartServiceOptions): Promise<{ title: string }>;
  stopService(): Promise<{value:string}>;
}

export interface StartServiceOptions {
  title:string,
  description:string,
  icon?:string,
  importance?:number,
  notificationId?:number
}