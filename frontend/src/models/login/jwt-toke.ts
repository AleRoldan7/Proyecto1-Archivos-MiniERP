export interface JwtPayLoad {
    sub: string;
    exp: number;
    iat?: number;
}